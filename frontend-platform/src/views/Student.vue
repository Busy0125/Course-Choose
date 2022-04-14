<template>
  <div>
    <Header></Header>
    <div style="display: flex;">
      <Aside></Aside>
      <router-view style="flex: 1;"/>
    </div>
  </div>
</template>

<script>
import Header from "@/components/Header";
import Aside from "@/components/AsideForStudent";

export default {
  name: "Student",
  components: {
    Header,
    Aside
  }
}
</script>

<style scoped>

</style>