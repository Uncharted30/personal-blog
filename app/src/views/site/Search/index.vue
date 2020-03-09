<template>
    <div class="main-content common-page clearfix">
        <div class="common-item">
            <div class="common-title" v-if="blogList != null && blogList.length > 0"> Search result for : {{keyword}}</div>
            <div class="common-title" v-else> No search result for : {{keyword}}</div>
            <div class="post-lists" v-if="blogList != null && blogList.length > 0">
                <div class="post-lists-body">
                    <div class="post-list-item" v-for="blog in blogList" :key="blog.id">
                        <div class="post-list-item-container">
                            <div class="item-label">
                                <div class="item-title">
                                    <router-link :to="'/blog/' + blog.id">{{blog.title}}</router-link>
                                </div>
                                <div class="item-meta clearfix">
                                    <div class="item-meta-date">{{blog.createTime}}</div>
                                    <div class="item-meta-ico bg-ico-code"
                                         :style="'background: url(' + img + ') no-repeat;background-size: 40px auto;'"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "index",
        data() {
            return {
                keyword: this.$route.params.keyword,
                blogList: null,
                img: require('@/assets/images/bg-ico.png'),
            }
        },
        created() {
          this.fetchData();
        },
        methods: {
            fetchData() {
                this.$axios.get('api/blog/search/' + this.keyword).then(response => {
                    if (response.data.code === 200) {
                        this.blogList = response.data.data;
                    } else {
                        this.$message.error('Failed to get blog list, please refresh the page and try again. ' + response.data.message);
                    }
                }).catch(e => {
                    this.$message.error('Failed to get blog list, please refresh the page and try again. ' + e);
                })
            }
        }
    }
</script>

<style scoped>
    .main-content {
        padding-top: 80px;
    }
</style>