<template>
    <svg xmlns="http://www.w3.org/2000/svg"
         :width="width"
         :height="height"
         viewBox="0 0 18 18"
         :aria-labelledby="iconName"
    >
  </svg>
</template>

<script>
    export default {
        name: "SvgIcons",
        props: {
            iconName: {
                type: String,
                default: 'box'
            },
            width: {
                type: [Number, String],
                default: 18
            },
            height: {
                type: [Number, String],
                default: 18
            },
            iconColor: {
                type: String,
                default: 'currentColor'
            }
        }
    }
</script>

<style scoped>

</style>