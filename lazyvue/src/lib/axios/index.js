import axios from 'axios'
import qs from 'qs'
let axiosUtil = {}
axiosUtil.install = function (Vue) {
    Vue.prototype.$post = function (url , data , callback) {
        if (!data) {
            data = {}
        }
        data = qs.stringify(data)
        axios.post(url, data, callback).then(result => {
            callback(result)
        })
    }
}
export default axiosUtil