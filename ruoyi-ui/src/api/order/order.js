import request from '@/utils/request'

// 查询用户订单列表
export function listOrder(query) {
  return request({
    url: '/order/order/list',
    method: 'get',
    params: query
  })
}

// 查询用户订单详细
export function getOrder(id) {
  return request({
    url: '/order/order/' + id,
    method: 'get'
  })
}

// 新增用户订单
export function addOrder(data) {
  return request({
    url: '/order/order',
    method: 'post',
    data: data
  })
}

// 修改用户订单
export function updateOrder(data) {
  return request({
    url: '/order/order',
    method: 'put',
    data: data
  })
}

// 删除用户订单
export function delOrder(id) {
  return request({
    url: '/order/order/' + id,
    method: 'delete'
  })
}
