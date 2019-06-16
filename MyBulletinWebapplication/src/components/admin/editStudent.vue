<template>
   <div class="container">
      <div class="row">
        <div class="col-12">
          <h1>Edit student: </h1>
          <div class="form-login align-center">      
              <span class="success">{{success}}</span>             
              <input v-model="student.name" type="text" placeholder="Student name and surename" id="name"/>
              <input v-model="student.email" type="email" placeholder="student email" id="email"/>
              <input v-model="student.tagId" type="text" placeholder="student tag id" id="tagId"/>
              <span class="error" v-for="error in errors">{{error}}</span>
              <button class="u-full-width button-primary" type="submit" v-on:click="saveStudent()" >Edit student</button>
          </div>
       </div>
    </div>
  </div>

</template>

<script>
  import axios from 'axios';
  export default {
    name: 'editStudent',
    data() {
      return {
          errors: [],
          success: '',
          student_id: this.$route.params.id,
          student: {
            name: '',
            email: '',
            tagId: '',
            _method: "PUT"
          }
      }
    },
    methods: {
      saveStudent() {
        if(!this.student.name){
          this.errors.push("Name is required");
          return;
        }
        if(!this.student.tagId){
          this.errors.push("Tag id is required");
          return;
        }
        if (!this.student.email) {
          this.errors.push('Email required.');
          return;
        } else if (!this.validEmail(this.student.email)) {
          this.errors.push('Valid email required.');
          return;
        }
        console.log(this.student);
        axios.post(this.$store.getters.getUrl + 'api/students/' + this.student_id, this.student)
        .then((response) =>{
          alert(response.data['updated']);
          this.getStudent(this.student_id);
        })
        .catch((error)=>{
          //this.$router.push('/login'); 
        });
      },
      getStudent(id) {
        axios.get(this.$store.getters.getUrl + 'api/students/' + id + '/edit')
         .then(function (response) {
            this.student.name = response.data[0].name;
            this.student.email = response.data[0].email;
            this.student.tagId = response.data[0].tagId;
            console.log(response.data[0]);
          }.bind(this))
          .catch((error)=>{
            //.$router.push('/login'); 
          });
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
      this.getStudent(1);
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
