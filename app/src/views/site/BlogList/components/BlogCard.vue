<template>
    <div id="app-container">
        <div class="post-list-item-container">
            <div class="item-thumb bg-deepgrey" v-if="blog.headerImgUrl != null && blog.headerImgUrl !== ''"
                 :style="'background-image:url(api' + blog.headerImgUrl + ');'"></div>
            <div v-else class="item-thumb bg-deepgrey"></div>
            <router-link :to="'/blog/' + blog.id">
                <div class="item-desc">
                    <p v-html="blog.content.replace(/<.*?>/g, ' ')" id="content"></p>
                </div>
            </router-link>
            <div class="item-slant reverse-slant bg-deepgrey"></div>
            <div class="item-slant"></div>
            <div class="item-label">
                <div id="info-group">
                    <div class="item-title">
                        <router-link :to="'/blog/' + blog.id" v-text="blog.title"></router-link>
                    </div>
                    <div id="view-category">
                        <div class="view">
                            <i class="far fa-eye fa-xs"></i>
                            <span id="number-views">{{blog.views}}</span>
                        </div>
                        <div class="item-meta clearfix">
                            <div class="item-meta-ico bg-ico-book"
                                 :style="'background: url(' + img +') no-repeat;background-size: 40px auto;'"></div>
                            <div class="item-meta-cat">
                                <router-link :to="'/category/' + blog.category.id">{{blog.category.name}}</router-link>
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
        name: "BlogCard",
        props: ['blog'],
        data() {
            return {
                img: require('@/assets/images/bg-ico.png')
            }
        },
    }
</script>

<style scoped>
    .far {
        color: #5f5f5f;
    }

    #info-group {
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        height: 100%;
    }

    .index-page .post-list-item-container .item-label {
        padding-bottom: 23px;
    }

    #number-views {
        margin-left: 3px;
        color: #5f5f5f;
        font-size: 14px;
    }

</style>