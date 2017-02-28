<template>
  <div class="container">
    <app-header></app-header>
    <app-address-form></app-address-form>
    <app-charts-carousel></app-charts-carousel>
  </div>
</template>

<script>
  import Header from './components/Header.vue';
  import AddressForm from './components/AddressForm.vue';
  import ChartsCarousel from './components/ChartsCarousel.vue';

  export default {
    data: function (){
      return {
      }
    },
    components: {
      appHeader: Header,
      appAddressForm: AddressForm,
      appChartsCarousel: ChartsCarousel
    },
    methods: {
    }
  }
</script>

<style>
</style>
