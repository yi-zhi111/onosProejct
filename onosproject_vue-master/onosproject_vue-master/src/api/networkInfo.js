import request from '@/utils/request'

export function getSwitchList() {
  return request({
    url: '/switchInfo',
    method: 'get'
  })
}

export function getControllerList() {
  return request({
    url: '/controllerInfo',
    method: 'get'
  })
}

export function getPkinRate() {
  return request({
    url: '/pkinRate',
    method: 'get'
  })
}

export function getPkoutRate() {
  return request({
    url: '/pkoutRate',
    method: 'get'
  })
}
