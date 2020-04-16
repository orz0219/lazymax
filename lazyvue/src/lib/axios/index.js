import axios from 'axios'
import qs from 'qs'
let axiosUtil = {}
axiosUtil.install = function (Vue) {
    Vue.prototype.$post = function (url , data , callback) {
        if (!data) {
            data = {}
        }
        data = qs.stringify(data)
        if (process.env.NODE_ENV === 'development') {
            url = "http://localhost:9091" + url
        }
        axios.post(url, data, callback).then(result => {
            callback(result)
        })
    }
}
export default axiosUtil