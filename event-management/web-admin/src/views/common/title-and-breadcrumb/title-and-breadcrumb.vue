<template>
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <h1>{{title}}</h1>
                </div>
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><router-link to="/">Home</router-link></li>
                        <li v-for="item in breadcrumb" :key="item.index" class="breadcrumb-item active">{{item.name}}</li>
                    </ol>
                </div>
            </div>
        </div><!-- /.container-fluid -->
    </section>
</template>

<script>
export default {
  name: 'ViewTitle',
  data() {
    return {
      title: '',
      breadcrumb: [],
    }
  },
  watch: {
    $route (to /*, from*/) {
      this.title = to.name;
      this.breadcrumb = [ ];
      if (to.fullPath!=='/') this.breadcrumb.push({ name: to.name, url: to.fullPath });
    }
  }
}
</script>