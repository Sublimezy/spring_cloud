import request from '@/utils/request'

// 查询店铺活动列表
export function listEvent(query) {
  return request({
    url: '/data/shopEvent/list',
    method: 'get',
    params: query
  })
}

// 查询店铺活动详细
export function getEvent(id) {
  return request({
    url: '/data/shopEvent/' + id,
    method: 'get'
  })
}

// 新增店铺活动
export function addEvent(data,userId) {
  return request({
    url: '/data/shopEvent/'+userId,
    method: 'post',
    data: data
  })
}

// 修改店铺活动
export function updateEvent(data) {
  return request({
    url: '/data/shopEvent',
    method: 'put',
    data: data
  })
}

// 删除店铺活动
export function delEvent(id) {
  return request({
    url: '/data/shopEvent/' + id,
    method: 'delete'
  })
}
