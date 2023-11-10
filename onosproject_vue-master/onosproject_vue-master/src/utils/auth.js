// https://github.com/js-cookie/js-cookie#basic-usage
// import Cookies from 'js-cookie'

export function getToken() {
  return localStorage.getItem('login-token')
}

export function setToken(token) {
  return localStorage.setItem('login-token', JSON.stringify(token))
}

export function removeToken() {
  return localStorage.removeItem('login-token')
}
