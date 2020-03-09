<template>
    <div id="app-container">
        <tag-category-blog-list type="category" url="/api/categories/"></tag-category-blog-list>
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