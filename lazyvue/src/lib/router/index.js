import Main from "@/components/Main";
import Api from "@/components/utils/Api";
import Code from "@/components/utils/Code";
import Setter from "@/components/utils/Setter";
export default [
    {path:'/', component: Main},
    {path:'/api',component: Api},
    {path:'/rpc',component: Code},
    {path:'/setter',component: Setter},
]