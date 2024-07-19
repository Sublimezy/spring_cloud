import request from '@/utils/request'

// 查询店铺类型列表
export function listType(query) {
  return request({
    url: '/data/shopType/list',
    method: 'get',
    params: query
  })
}

// 查询店铺类型详细
export function getType(id) {
  return request({
    url: '/data/shopType/' + id,
    method: 'get'
  })
}

// 新增店铺类型
export function addType(data) {
  return request({
    url: '/data/shopType',
    method: 'post',
    data: data
  })
}

// 修改店铺类型
export function updateType(data) {
  return request({
    url: '/data/shopType',
    method: 'put',
    data: data
  })
}

// 删除店铺类型
export function delType(id) {
  return request({
    url: '/data/shopType/' + id,
    method: 'delete'
  })
}
