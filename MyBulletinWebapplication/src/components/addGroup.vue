<template>
   <div class="container">
      <div class="row">
        <div class="col-12">
          <h1>Add new group: </h1>
          <div class="form-login align-center">      
              <span class="success">{{success}}</span>             
              <input v-model="group.name" type="text" placeholder="Group name" id="name"/>          
              <span class="error" v-for="error in errors">{{error}}</span>
              <button class="u-full-width button-primary" type="submit" v-on:click="addGroup" >Add group</button>
          </div>
       </div>
    </div>
  </div>

</template>

<script>
  import axios from 'axios';
  export default {
    name: 'addGroup',
    data() {
      return {
          errors: [],
          success: '',
          group: {
            name: '',
            professor_id: localStorage.getItem('professor_id')
          }
      }
    },
    methods: {
      addGroup() {
        this.errors = [];
        if(!this.group.name){
          this.errors.push("Group name is required");
          return;
        }
       axios.post(this.$store.getters.getUrl + 'api/groups', this.group).then((response) =>{
          this.success = response.data['created'];
        })
        .catch((error)=>{
          this.$router.push('login'); 
        });
      }
      
    },
    created: function () {
      if(!localStorage.getItem('token') || localStorage.getItem('isAdmin') == 1){
        this.$router.push('/login'); 
      }
    }
  }
</script>
<style scoped>
h1, h2 {
  font-size: 50px;
  text-align: center;
  width: 100%;
  margin: 30px 0;
}
.form{
  max-width: 500px;
  margin: 0 auto;
}
.error{
  color: red;
  display: block;
}
.success{
  display: block;
  margin-bottom: 30px;
  color: green;
}
#name{
  width: 100%;
  max-width: 400px;
  display: block;
  margin: 0 auto;
  padding: 10px;
  border: 1px solid #c4c4ef;
  border-radius: 30px;
  text-align: center;
  margin-bottom: 15px;
  outline: none;
}
 #name:focus, #name:active{
  border-color: #00529c;
}
.button-primary{
  border: 1px solid #c4c4ef;
  border-radius: 30px;
  background-color: transparent;
  padding: 10px 50px;
  margin-top: 20px;
  transition: 0.4s;
  outline: none;
  cursor: pointer;
}
.button-primary:focus, .button-primary:active, .button-primary:hover{
  border-color: #00529c;
  color: #00529c;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
