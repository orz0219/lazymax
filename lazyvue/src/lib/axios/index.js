import axios from 'axios'
import qs from 'qs'

// request拦截器
axios.interceptors.request.use(config => {
        if (config.method === 'post') {
            // 克隆对象解决原值改变的问题
            let data = Object.assign({}, config.data)
            if (data) {
                Object.keys(data).forEach(key=>{
                    let value = data[key]
                    if (!value || value.length === 0) {
                        delete data[key]
                    }
                    if (typeof value === 'object') {
                        data[key] = JSON.stringify(data[key])
                    }
                })
            }
            config.data = qs.stringify(data)
        }
        return config
    },
    error => {
        Promise.reject(error)
    }
)

let AjaxUtil = {}
AjaxUtil.install = function (Vue) {
    Vue.prototype.$post = function (url , data , callback){
        if (!data) {
            data = {}
        }
        const baseUrl = this.$store.getters.getUrl
        if (!url.startsWith("/")) {
            url = "/" + url
        }
        url = baseUrl + url
        axios.post(url, data , callback).then(response => {
            let data = response.data
            if (data && data.success) {
                if (url.indexOf('select') === -1) {
                    if (!url.endsWith('doIt')) {
                        this.$message({
                            message: data.message,
                            type: 'success'
                        })
                    }
                }
                let total = data['pageTotal']
                if (callback) callback(data.object, total ? total : 0)
            } else {
                this.$message.error(data.message)
            }
        }).catch(e => {
            // eslint-disable-next-line
            console.log(e)
            this.$message.error('加载失败,请联系管理员!')
        })
    }
    Vue.prototype.$save = function (url , data , callback) {
        this.$simpleConfirm('是否确认保存这些数据?', '保存', () => {
            this.$post(url , data , callback)
        })
    }
}

export default AjaxUtil