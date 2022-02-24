import Clock from '@/components/clock/clock.vue';

export default {
  name: 'Header',
  props: {
    links: Array,
    showClock: { type: Boolean, default: false }
  },
  components: { Clock },
}
