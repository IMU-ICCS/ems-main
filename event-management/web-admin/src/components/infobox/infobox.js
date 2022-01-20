
export default {
  name: 'infobox',
  props: {
    value: String,
    id: String,
    classes: String,
    message: String,
    text_before: String,
    text_default: String,
    text_after: String,
    bg_class: String,
    icon_classes: String,
    loading: Boolean,
    style: { type: String, default: 'padding:0;' }
  }
}
