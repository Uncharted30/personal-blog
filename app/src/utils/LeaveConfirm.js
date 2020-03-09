export default {
    beforeRouteLeave(to, form, next) {
        setTimeout(() => {
            this.$confirm('You have unsaved change, do you really want to leave this page?').then(() => {
                next();
            }).catch(e => {
                console.log(e);
            });
        })
    }
}