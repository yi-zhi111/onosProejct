import { login, getJwt, register } from '@/api/user'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { resetRouter } from '@/router'
import { nanoid } from 'nanoid'

const getDefaultState = () => {
  return {
    token: getToken(),
    name: '',
    avatar: ''
  }
}

const state = getDefaultState()

const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  },
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  }
}

const actions = {
  // user login
  login({ commit }, userInfo) {
    const { username, password } = userInfo
    return new Promise((resolve, reject) => {
      login({ username, password }).then(response => {
        const { token, username } = response.data
        if (!response.data) {
          console.log('error')
          return reject('验证失败，请重新登录')
        }
        localStorage.setItem('login-token', response.data.token)
        commit('SET_TOKEN', token)
        commit('SET_NAME', username)
        commit('SET_AVATAR', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif')
        setToken(token)
        resolve(response.data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // get user info
  getJwt({ commit, state }) {
    return new Promise((resolve, reject) => {
      getJwt(JSON.parse(state.token)).then(response => {
        const { data } = response
        if (!data || data === false) {
          return reject('验证失败，请重新登录')
        }
        const { username } = data
        commit('SET_NAME', username)
        commit('SET_AVATAR', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif')
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },
  // user register
  register({ commit }, userInfo) {
    // 解构数据
    const { username, password, address, birth, tel, email } = userInfo
    return new Promise((resolve, reject) => {
      register({ id: nanoid(5), username, password, address, birth, tel, email }).then(response => {
        resolve(response.data)
      }).catch(error => {
        reject(error)
      })
    })
  },
  // user logout
  logout({ commit }) {
    return new Promise((resolve, reject) => {
      removeToken() // must remove token first
      resetRouter()
      commit('RESET_STATE')
      resolve()
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      removeToken() // must remove  token  first
      commit('RESET_STATE')
      resolve()
    })
  }
}

export default {
  namespaced: true, // 不写的话，mapGetters里面拿不到
  state,
  mutations,
  actions
}

