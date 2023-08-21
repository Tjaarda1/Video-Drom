<template>
<!-- https://dev.to/erefor/vue-3-v-model-on-custom-input-type-file-5b1e -->
  <div>
    <label for="inputFile" class="form-label">{{ labelText }}</label>
    <input type="file" class="form-control" @change="onChangeFile" name="inputFile" id="inputFile" />
  </div>
    
  
</template>
  
  <script>
  export default{
    name: "FileInput",
    props: ['labelText'],
    methods: {
      onChangeFile(event) {
        this.$emit('update:modelValue', event.target.files[0])
      }
    }
  }
   
  </script>
  
 
  