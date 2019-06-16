<template>
   <div class="container">
      <div class="row">
        <div class="col-12">
          <h1>Add new student: </h1>
          <div class="form-login align-center">      
              <span class="success">{{success}}</span>      
              <div id="form-center" >
              <inputForms v-for="input in inputs" :props=input v-bind:input="input" v-bind:key="input" @save="saveRecord"/>
          
              </div>
              <button class="u-full-width button-primary" v-on:click="addRow" >More</button>
              <span class="error" v-for="error in errors">{{error}}</span>
              <button class="u-full-width button-primary" type="submit" v-on:click="addStudent" >Add student</button>
          </div>
       </div>
    </div>
  </div>

</template>

<script>
  import axios from 'axios';
  import Vue from 'vue';
  import inputForms from './inputForms';
import Vuex from 'vuex';

Vue.use(Vuex)
  export default {
    name: 'addstudent',
    components: { inputForms },
    data() {
      return {
          errors: [],
          success: '',
          i: 0,
          groups: [],
          students: [],
          inputs: 1
      }
    },
    methods: {
      addRow(){
        this.inputs++;

      },
      saveRecord(student){
        this.students[student.id-1] = student;
      },
      addStudent() {
      this.errors = [];
        let studentTemps =[]
        for(var i = 0; i < this.students.length; i++){
          let studentTemp= {name: this.students[i].name, email: this.students[i].email, tagId: this.students[i].tagId}; 
          studentTemps.push(studentTemp);
          if(!studentTemp.name){
            this.errors.push("Name is required");
            return;
          }
          if(!studentTemp.tagId){
            this.errors.push("Tag id is required");
            return;
          }
          if (!studentTemp.email) {
            this.errors.push('Email required.');
            return;
          } else if (!this.validEmail(studentTemp.email)) {
            this.errors.push('Valid email required.');
            return;
          }
          if(studentTemps.length == this.students.length){
            this.saveStudents(studentTemps);
          }
        }

      },
      saveStudents(studentTemps){
       for(var i = 0; i < studentTemps.length; i++){
            axios.post((this.$store.getters.getUrl + 'api/students'), studentTemps[i]).then((response) =>{
              this.success = response.data['created'];
              console.log(response.data);
            })
            .catch(function(error){
              this.errors.push("Something went wrong");
            }.bind(this));
          }
      },
      validEmail(email){
        var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return re.test(email);
      }
    },
    created: function () {
      if(!localStorage.getItem('token') || localStorage.getItem('isAdmin') != 1){
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
input{
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
select{
  text-align-last:center;
}
#groupId option{
  text-align: center;
}
 #name:focus, #tagId:focus, #groupId:focus, #name:active, #tagId:active, #groupId:active{
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
