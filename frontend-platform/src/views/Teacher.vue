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
import Aside from "@/components/AsideForTeacher";

export default {
  name: "Teacher",
  components: {
    Header,
    Aside
  }
}
</script>

<style scoped>

</style>