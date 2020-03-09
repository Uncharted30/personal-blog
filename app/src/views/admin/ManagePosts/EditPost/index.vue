<template>
    <editor :id="$route.params.id" :type="'put'" ref="editor"></editor>
</template>

<script>
    import Editor from '@/components/Editor/index'

    export default {
        name: "index",
        components: {
            Editor
        },
        beforeRouteLeave(to, from, next) {
            if (this.$refs.editor.changed) {
                setTimeout(() => {
                    this.$confirm('You have unsaved change, do you really want to leave this page?').then(() => {
                        next();
                    }).catch(e => {
                        console.log(e);
                    });
                })
            } else {
                next();
            }
        }
    }
</script>

<style scoped>

</style>