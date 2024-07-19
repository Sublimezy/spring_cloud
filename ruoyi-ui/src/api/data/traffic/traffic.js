import request from '@/utils/request'

// 查询交通信息列表
export function listTraffic(query) {
  return request({
    url: '/data/traffic/list',
    method: 'get',
    params: query
  })
}

// 查询交通信息详细
export function getTraffic(id) {
  return request({
    url: '/data/traffic/' + id,
    method: 'get'
  })
}

// 新增交通信息
export function addTraffic(data) {
  return request({
    url: '/data/traffic',
    method: 'post',
    data: data
  })
}

// 修改交通信息
export function updateTraffic(data) {
  return request({
    url: '/data/traffic',
    method: 'put',
    data: data
  })
}

// 删除交通信息
export function delTraffic(id) {
  return request({
    url: '/data/traffic/' + id,
    method: 'delete'
  })
}
