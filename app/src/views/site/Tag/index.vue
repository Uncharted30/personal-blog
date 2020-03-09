<template>
    <div id="app-container">
        <tag-category-blog-list type="tag" url="api/tags/"></tag-category-blog-list>
    </div>
</template>

<script>
    import TagCategoryBlogList from '@/components/TagCategoryBlogList'
    export default {
        name: "index",
        components: {
            TagCategoryBlogList
        }
    }
</script>

<style scoped>

</style>