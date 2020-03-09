<template>
    <div class="main-content archive-page clearfix">
        <div class="categorys-item">
        <span v-for="sameMonth in list" :key="sameMonth[0].createTime">
            <div class="categories-title">{{new Date(sameMonth[0].createTime).format('MM/yyyy')}}</div>
            <div class="post-lists">
                <div class="post-lists-body">
                    <div class="post-list-item" v-for="blog in sameMonth" :key="blog.id">
                        <div class="post-list-item-container">
                            <div class="item-label">
                                <div class="item-title">
                                    <router-link :to="'/blog/' + blog.id">{{blog.title}}</router-link>
                                </div>
                                <div class="item-meta clearfix">
                                    <div class="item-meta-date">{{blog.createTime}}</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </span>
        </div>
    </div>

</template>

<script>

    Date.prototype.format = function(fmt) {
        let o = {
            "M+" : this.getMonth()+1,                 //月份
            "d+" : this.getDate(),                    //日
            "h+" : this.getHours(),                   //小时
            "m+" : this.getMinutes(),                 //分
            "s+" : this.getSeconds(),                 //秒
            "q+" : Math.floor((this.getMonth()+3)/3), //季度
            "S"  : this.getMilliseconds()             //毫秒
        };
        if(/(y+)/.test(fmt)) {
            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
        }
        for(var k in o) {
            if(new RegExp("("+ k +")").test(fmt)){
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
            }
        }
        return fmt;
    };

    export default {
        name: "index",
        data() {
            return {
                list: null
            }
        },
        created() {
            this.fetchData()
        },
        methods: {
            fetchData() {
                this.$axios.get('api/blog/archive').then(response => {
                    if (response.data.code === 200) {
                        this.list = response.data.data;
                        console.log(this.list)
                    } else {
                        this.$message.error('Failed to get blog archives, please refresh page and try again. ' + response.data.message);
                    }
                }).catch(e => {
                    this.$message.error('Failed to get blog archives, please refresh page and try again. ' + e);
                })
            }
        }
    }
</script>
<style scoped>
    @import "~@/assets/styles/style.min.css";

    .archive-page {
        padding-top: 80px;
    }
</style>
