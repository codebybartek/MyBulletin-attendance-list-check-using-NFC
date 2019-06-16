<template>
   <div class="container">
      <div class="row">
        <div class="col-12">
          <h1>Add new subect: </h1>
          <div class="form-login align-center">      
              <span class="success">{{success}}</span>             
              <input v-model="subject.name" type="text" placeholder="Subject name" id="name"/>
              <span class="error" v-for="error in errors">{{error}}</span>
              <button class="u-full-width button-primary" type="submit" v-on:click="addSubject" >Add Subject</button>
          </div>
       </div>
    </div>
  </div>

</template>

<script>
  import axios from 'axios';
  export default {
    name: 'addSubject',
    data() {
      return {
        errors: [],
        success: '',
        subject: {
          name: '',
          professor_id: 0
        }
      }
    },
    methods: {
      addSubject() {
        this.errors = [];
        if(!this.subject.name){
          this.errors.push("Subject name is required");
          return;
        }
        this.subject.professor_id = localStorage.getItem('professor_id');
        axios.post(this.$store.getters.getUrl + 'api/subjects', this.subject).then((response) =>{
          this.success = response.data['created'];
        })
        .catch(function(error){
          this.errors.push("Something went wrong");
        }.bind(this));
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
#name, #tagId, #groupId{
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
