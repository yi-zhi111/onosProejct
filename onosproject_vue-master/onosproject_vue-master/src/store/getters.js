// 和计算属性很像，从state的数据中派生（比如需要进行计算等操作）出其他的数据
const getters = {
  sidebar: state => state.app.sidebar, // state:方法名，返回state.app.sidebar
  device: state => state.app.device,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  name: state => state.user.name
}
export default getters
