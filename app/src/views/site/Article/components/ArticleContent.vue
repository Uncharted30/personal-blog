<template>
    <article class="main-content page-page">
        <div class="post-header">
            <h1 class="post-title" itemprop="name headline"> {{article.title}} </h1>
            <div class="view">
                <i class="far fa-eye fa-xs"></i>
                <span id="number-views">{{article.views}}</span>
            </div>
            <div class="post-data">
                <time datetime="2018-04-01T23:34:00+08:00" itemprop="datePublished">{{article.createTime}}</time>
                in <router-link :to="'/category/' + article.category.id">{{article.category.name}}</router-link>
            </div>
        </div>
        <div class="post-content" itemprop="articleBody">
            <p class="post-tags" v-if="article.tags != null">
                <router-link v-for="tag in article.tags" :to="'/tag/' + tag.id" :key="tag.id">{{tag.name}}</router-link>
            </p>

            <div id="post-content" v-html="article.content" ref="content"></div>

            <div v-if="article.original">
                <p class="post-info">This is an article authored by <a href="/">{{article.author}}</a>.</p>
                <p>Material on this website is licensed under the
                    <a href="https://creativecommons.org/licenses/by/4.0/" target="_blank" rel="external nofollow">Creative
                        Commons Attribution 4.0</a> International licence</p>
                <p>The last edit time is: {{article.updateTime}}</p>
            </div>
            <div v-else>
                <p class="post-info">This is an reprinted article, authored by {{article.author}}.</p>
                <p>Original article could be found <a :href="article.originalUrl">here</a></p>
                <p>The last edit time is: {{article.updateTime}}</p>
            </div>
        </div>
    </article>
</template>

<script>
    export default {
        name: "ArticleContent",
        props: ['article']
    }
</script>

<style scoped>
    .page-page {
        padding-top: 50px;
    }

    .post-info {
        padding-bottom: 0;
    }

    #number-views {
        margin-left: 3px;
        color: #5f5f5f;
    }

    .far {
        color: #5f5f5f;
    }
</style>