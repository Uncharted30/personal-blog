<template>
    <div class="index-page main-content">
        <div class="post-lists">
            <div class="post-lists-body">
                <div class="post-list-item" v-if="blogList == null || blogList.length === 0">
                    <p>Where are the posts...</p>
                </div>
                <div class="post-list-item" v-else v-for="item in blogList" :key="item.id">
                    <BlogCard :blog="item"></BlogCard>
                </div>
            </div>
        </div>
        <div class="block">
            <el-pagination
                    layout="prev, pager, next"
                    :total="totalElements"
                    :page-size="9"
                    class="blog-pagination"
                    @current-change="handleCurrentChange">
            </el-pagination>
        </div>
        <br>
    </div>
</template>

<script>
    import BlogCard from "@/views/site/BlogList/components/BlogCard";

    export default {
        name: "index",
        data() {
            return {
                blogList: null,
                pageNumber: 0,
                totalElements: 0,
            }
        },
        created() {
            this.getBlogList();
        },
        methods: {
            getBlogList() {
                this.$axios.get('api/blog/list/9/' + this.pageNumber, {
                    params: {
                        title: this.keyword
                    }
                }).then(response => {
                    if (response.data.code === 200) {
                        this.blogList = response.data.data.content;
                        this.totalElements = response.data.data.totalElements;
                    } else {
                        this.$message.error('Failed to get blog list, please try again. ' + response.data.message);
                    }
                }).catch(e => {
                    this.$message.error('Failed to get blog list, please try again. ' + e);
                })
            },
            handleCurrentChange(newCurrent) {
                this.pageNumber = newCurrent - 1;
                this.getBlogList();
            }
        },
        components: {
            BlogCard
        },
        props: ['keyword']
    }
</script>
<style lang="css" scoped>
    .block {
        margin-top: 20px;
        text-align: center;
    }

    .index-page {
        padding-top: 80px;
    }
</style>

<style>
    .blog-pagination .btn-prev:disabled {
        background-color: rgb(246, 246, 246);
    }

    .blog-pagination .btn-next:disabled {
        background-color: rgb(246, 246, 246);
    }

    .blog-pagination .btn-prev {
        background-color: rgb(246, 246, 246);
    }

    .blog-pagination .btn-next {
        background-color: rgb(246, 246, 246);
    }
    .blog-pagination .el-pager .number {
        background-color: rgb(246, 246, 246);
    }
</style>