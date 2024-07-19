package com.ruoyi.data.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.constant.HttpStatus;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.redis.service.RedisService;
import com.ruoyi.data.entity.dos.Shop;
import com.ruoyi.data.entity.dos.ShopEvent;
import com.ruoyi.data.entity.vos.ShopListVO;
import com.ruoyi.data.mapper.ShopEventMapper;
import com.ruoyi.data.mapper.ShopMapper;
import com.ruoyi.data.service.IShopService;
import com.ruoyi.distance.api.RemoteAppUserService;
import com.ruoyi.distance.api.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.domain.geo.GeoReference;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.ruoyi.common.core.constant.CacheConstants.SHOP_GEO_KEY;


/**
 * 店铺Service业务层处理
 *
 * @author zhangyan
 * @date 2024-06-08
 */
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements IShopService {

    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private RemoteAppUserService remoteAppUserService;

    @Autowired
    private RedisService redisService;

    @Autowired
    public RedisTemplate redisTemplate;
    @Autowired
    public ShopEventMapper shopEventMapper;

    /**
     * 查询店铺
     *
     * @param id 店铺主键
     * @return 店铺
     */
    @Override
    public Shop selectShopById(String id) {
        return shopMapper.selectShopById(id);
    }

    /**
     * 查询店铺列表
     *
     * @param shop 店铺
     * @return 店铺
     */
    @Override
    public List<Shop> selectShopList(Shop shop) {
        return shopMapper.selectShopList(shop);
    }

    /**
     * 新增店铺
     *
     * @param shop 店铺
     * @return 结果
     */
    @Override
    public int insertShop(Shop shop) {

        //修改del_flag是否已注册商户
        R<?> registerResult = remoteAppUserService.getAddShopInfo(shop.getUserId(), SecurityConstants.INNER);
        User user = (User) registerResult.getData();
        shop.setUserId(user.getUserId());
        shop.setUserName(user.getUserName());
        shop.setCreateBy(user.getNickName());
        shop.setCreateTime(DateUtils.getNowDate());
        shop.setScore(5);
        if (shop.getImages() == null) {
            System.out.println("图片空");
            shop.setImages("http://127.0.0.1:9000/shop2/2024/06/10/QQ%E6%88%AA%E5%9B%BE20231012132920_20240610223651A005.png");

        }
        if (shop.getAddress() == null) {
            System.out.println("getAddress空");
            shop.setAddress("未填写地址");
        }
        if (shop.getArea() == null) {
            System.out.println("getArea空");
            shop.setArea("未填写地址");
        }


        return shopMapper.insertShop(shop);
    }

    /**
     * 修改店铺
     *
     * @param shop 店铺
     * @return 结果
     */
    @Override
    public int updateShop(Shop shop) {
        shop.setUpdateTime(DateUtils.getNowDate());
        return shopMapper.updateShop(shop);
    }

    /**
     * 批量删除店铺
     *
     * @param ids 需要删除的店铺主键
     * @return 结果
     */
    @Override
    public int deleteShopByIds(String[] ids) {
        return shopMapper.deleteShopByIds(ids);
    }

    /**
     * 删除店铺信息
     *
     * @param id 店铺主键
     * @return 结果
     */
    @Override
    public int deleteShopById(String id) {
        return shopMapper.deleteShopById(id);
    }

    @Override
    public Shop getInfoByUserId(Long id) {
        QueryWrapper<Shop> shopQueryWrapper = new QueryWrapper<>();
        shopQueryWrapper.eq("user_id", id);
        return getOne(shopQueryWrapper);

    }

    @Override
    public Boolean loadShopData() {
        //1.查询店铺信息

        List<Shop> list = list();


        //2.把店铺分组，按照typeId分组，id一致的放到一个集合

        Map<String, List<Shop>> map = list.stream().collect(Collectors.groupingBy(Shop::getType));

        //3.分批完成写入Redis

        for (Map.Entry<String, List<Shop>> entry : map.entrySet()) {
            //获取类型id
            String typeId = entry.getKey();

            String key = SHOP_GEO_KEY + typeId;
            //获取同类型的集合
            list = entry.getValue();

            List<org.springframework.data.redis.connection.RedisGeoCommands.GeoLocation<String>> locations = new ArrayList<>(list.size());

            //写入redis
            for (Shop shop : list) {
                //   stringRedisTemplate.opsForGeo().add(key, new Point(shop.getX(), shop.getY()), shop.getId().toString());
                locations.add(new RedisGeoCommands.GeoLocation<>(shop.getId().toString(), new Point(Double.parseDouble(shop.getX()), Double.parseDouble(shop.getY()))));
            }

            redisService.opsForGeo(key, locations);

        }


        return true;
    }


    @Override
    public TableDataInfo queryShopList(Integer typeId, Integer pageNum, Integer pageSize, Double x, Double y, Double distance) {

        loadShopData();
        TableDataInfo rspData = new TableDataInfo();
        //1.判断是否需要根据些标查询
        if (x == null || y == null) {
            Page<Shop> page = query()
                    .eq("type", typeId)
                    .page(new Page<>(pageNum, pageSize));
            // 返回数据

            rspData.setCode(HttpStatus.SUCCESS);
            rspData.setRows(page.getRecords());
            rspData.setMsg("查询成功");
            rspData.setTotal(page.getTotal());

            return rspData;
        }

        String key = SHOP_GEO_KEY + typeId;
        //2.计算分页参数

        //开始
        int from = (pageNum - 1) * pageSize;
        //结束
        int end = pageNum * pageSize;


        //3.查询redis、按照距离排序、分页。结果:shopId、distance
        // GEOSEARCH BYLONLAT X Y BYRADIUS 1O WITHDISTANCE

        //添加 GEOADD g1 116.378248 39.865275 bjn 116.42803 39.903738 bjz 116.322287 39.893729 bjx
        //计算距离 GEODIST g1 bjn bjx

        GeoResults<RedisGeoCommands.GeoLocation<String>> results = redisTemplate.opsForGeo()
                .search(
                        key,
                        GeoReference.fromCoordinate(x, y),
                        new Distance(distance),
                        RedisGeoCommands.GeoSearchCommandArgs.newGeoSearchArgs()
                                .includeDistance()
                                .includeCoordinates()
                                .limit(end)
                );
        // 4.解析出id
        if (results == null) {
            return rspData;
        }

        List<GeoResult<RedisGeoCommands.GeoLocation<String>>> list = results.getContent();
        if (list.size() <= from) {
            //没有下一页
            return rspData;
        }

        //list是从1-end截的，所以我们要手动截 from ~ end
        Integer total = list.size();
        List<Long> ids = new ArrayList<>(list.size());

        Map<String, Distance> distanceMap = new HashMap<>(list.size());

        list.stream().skip(from).forEach(result -> {

                    //店铺ID
                    String shopIdStr = result.getContent().getName();
                    ids.add(Long.valueOf(shopIdStr));

                    //获取距离
                    distanceMap.put(shopIdStr, result.getDistance());


                }
        );
        //5.根据id查询Shop
        String idStr = StrUtil.join(",", ids);
        List<Shop> shopList = query().in("id", ids)
                .last("ORDER BY FIELD(id," + idStr + ")")
                .list();
        for (Shop shop : shopList) {
            shop.setGeo(distanceMap.get(shop.getId().toString()).getValue());
            shop.setCount(5);
            shop.setRate((int) (double) shop.getScore());
        }

        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setRows(shopList);
        rspData.setMsg("查询成功");
        rspData.setTotal(list().size());

        return rspData;

    }

    @Override
    public TableDataInfo selectIndexList() {

        List<Shop> shopList = list();

        List<ShopEvent> shopEventsList = shopEventMapper.selectList(new QueryWrapper<>());

        List<ShopListVO> mixedList = new ArrayList<>();

        int shopListSize = shopList.size();
        int shopEventListSize = shopEventsList.size();
        int s = 0;
        int e = 0;
        for (int i = 0; i < shopListSize + shopEventListSize; i++) {
            if (s < shopList.size()) {
                Shop shop = shopList.get(s);
                ShopEvent shopEvent = new ShopEvent();
                shopEvent.setEventLogo(shop.getImages());
                shopEvent.setEventName(shop.getName());
                shopEvent.setScore(shop.getScore());
                shopEvent.setShopId(shop.getId());
                mixedList.add(new ShopListVO(shopEvent, false));
                s++;
            }
            if (e < shopEventsList.size()) {
                ShopEvent shopEvent = shopEventsList.get(e);
                shopEvent.setScore(95);
                if (shopEvent.getIsDiscount() == 1) {
                    shopEvent.setOriginAmount(Integer.parseInt(shopEvent.getAmount()) / shopEvent.getDiscountRate().intValue());
                }
                mixedList.add(new ShopListVO(shopEvent, true));
                e++;
            }
        }


        return new TableDataInfo(mixedList, mixedList.size());
    }

}

