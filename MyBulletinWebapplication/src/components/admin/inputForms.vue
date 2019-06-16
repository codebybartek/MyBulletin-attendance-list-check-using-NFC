<template>
  <div class="row" style="margin-top: 15px;"> 
  	<div class="col-4">
  		<input v-model="student.name" type="text" placeholder="Student name and surename" id="name"/>
  	</div>
  	<div class="col-4">
  		<input v-model="student.email" type="email" placeholder="Student email" id="email"/> 
  	</div>
  	<div class="col-4">
  		<input v-model="student.tagId" type="text" placeholder="Student tag id" id="tagId"/> 
  	</div>
  </div>
</template>

<script>
  export default {
	name: 'inputForms',
	props: ['input'],
	data() {
      return {
        student:  {
        	name: '',
        	email: '',
        	tagId: '',
        	id: null
      	}
      }
    },
    methods: {

    },
    watch: {
  	'student.name': function() {
  		this.student.id = this.input;
      	this.$emit('save', this.student);
  
  	  }
	},
}
</script>
<style scoped>
button.primary {
background: blue;
color: white;
}
</style>