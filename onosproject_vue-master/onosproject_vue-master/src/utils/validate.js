/**
 * Created by PanJiaChen on 16/11/18.
 */

/**
 * @param {string} path
 * @returns {Boolean}
 */
export function isExternal(path) {
  return /^(https?:|mailto:|tel:)/.test(path)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
/* export function validUsername(str) {
  // 登录校验，只有以下用户才能登录
  const valid_map = ['admin', 'editor', 'Ryu']
  return valid_map.indexOf(str.trim()) >= 0
} */

