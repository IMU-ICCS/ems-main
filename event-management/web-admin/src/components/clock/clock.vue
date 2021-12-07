<template>
    <SevenSegmentDisplay :useOffShade="true">
        {{time}}
    </SevenSegmentDisplay>
</template>

<script>
import SevenSegmentDisplay from '@/components/7seg/7seg.vue';

export default {
    name: 'Digital clock',
    props: {
        hasSeconds: Boolean
    },
    components: { SevenSegmentDisplay },
    data() {
        return {
            time: '',
        };
    },
    mounted() {
        this.interval = setInterval(() => {
            let dt = new Date();
            let sep = dt.getMilliseconds()<500 ? ':' : ' ';
            this.time =
                    new String(100+dt.getHours()).substr(1) + sep +
                    new String(100+dt.getMinutes()).substr(1);
            if (this.hasSeconds)
                this.time +=
                    sep + new String(100+dt.getSeconds()).substr(1);
        }, 500);
    },
    beforeUnmount() {
        clearInterval(this.interval);
    }
}
</script>