
export default {
  name: 'card',
  props: {
    id: String,
    classes: String,
    style: String,
    bodyClasses: String,
    header: String,
    icon: String,
    title: String,
    footer: String,
    links: Object,
    hasRefresh: Boolean,
    hasCollapse: Boolean,
    hasMaximize: Boolean,
    hasRemove: Boolean,
    runRefresh: String
  },
  methods: {
    doRefresh() {
        if (this.runRefresh && this.runRefresh!=='')
            eval(this.runRefresh);      // Can access local scope (and variables)
            //new Function( 'return (' + this.runRefresh + ')' )();     // Cannot access local scope (and variables)
    }
  }
}
