import request from '@/utils/request'

// 查询用户评价列表
export function listComments(query) {
  return request({
    url: '/comments/comments/list',
    method: 'get',
    params: query
  })
}

// 查询用户评价详细
export function getComments(id) {
  return request({
    url: '/comments/comments/' + id,
    method: 'get'
  })
}

// 新增用户评价
export function addComments(data) {
  return request({
    url: '/comments/comments',
    method: 'post',
    data: data
  })
}

// 修改用户评价
export function updateComments(data) {
  return request({
    url: '/comments/comments',
    method: 'put',
    data: data
  })
}

// 删除用户评价
export function delComments(id) {
  return request({
    url: '/comments/comments/' + id,
    method: 'delete'
  })
}
