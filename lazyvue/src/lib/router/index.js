import Main from "@/components/Main";
import Api from "@/components/utils/Api";
import Code from "@/components/utils/Code";
import Setter from "@/components/utils/Setter";
import History from "@/components/utils/History";
import Database from "@/components/utils/Database";
export default [
    {path:'/', component: Main},
    {path:'/api',component: Api},
    {path:'/rpc',component: Code},
    {path:'/setter',component: Setter},
    {path:'/history',component: History},
    {path:'/dbUtils',component: Database},
]