<template>
    <div class="main-content common-page clearfix">
        <div class="common-item">
            <div class="common-title" v-if="info !== null"> {{type}} : {{info.name}}</div>
            <div class="post-lists">
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
                info: null,
                blogList: null,
                img: require('@/assets/images/bg-ico.png'),
                id: this.$route.params.id
            }
        },
        created() {
            this.fetchData();
        },
        methods: {
            fetchData() {
                this.$axios.get(this.url + this.id).then(response => {
                    if (response.data.code === 200) {
                        this.info = response.data.data;
                    } else {
                        this.$message.error('Failed to get ' + this.type + ' info, please refresh the page and try again. ' + response.data.message);
                    }
                }).catch(e => {
                    this.$message.error('Failed to get ' + this.type + ' info, please refresh the page and try again. ' + e);
                });
                this.$axios.get('api/blog/' + this.type + '/' + this.id).then(response => {
                    if (response.data.code === 200) {
                        this.blogList = response.data.data;
                    } else {
                        this.$message.error('Failed to get blog list, please refresh the page and try again. ' + response.data.message);
                    }
                }).catch(e => {
                    this.$message.error('Failed to get blog list, please refresh the page and try again. ' + e);
                });
            }
        },
        props: ['type', 'url']
    }
</script>

<style scoped>
    .main-content {
        padding-top: 80px;
    }

    .common-title {
        text-transform: capitalize;
    }
</style>