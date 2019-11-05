import Main from "@/components/Main";
import Api from "@/components/utils/Api";
import Code from "@/components/utils/Code";
export default [
    {path:'/', component: Main},
    {path:'/api',component: Api},
    {path:'/rpc',component: Code},
]