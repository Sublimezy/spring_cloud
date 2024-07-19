import request from "@/utils/request";

export function fileUploadApi(data) {
  return request({
    url: '/file/upload',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}
