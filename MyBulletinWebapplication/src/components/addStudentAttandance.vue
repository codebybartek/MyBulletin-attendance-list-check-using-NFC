<template>
   <div class="container">
      <div class="row">
        <div class="col-12">
          <h1>Add new group: </h1>
          <div class="form-login align-center">      
              <span class="success">{{success}}</span>             
              <select v-model="student.student_id" id="student_id">    
                <option value="0" disabled selected>Chose Group</option>   
                <option v-for="student in students" :value="student.student_id">
                  {{student.name}}
                </option>                                                   
              </select>        
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
    name: 'addStudentAttandance',
    data() {
      return {
          errors: [],
          success: '',
          students: '',
          student: {
            student_id: null,
            activity_id: null,
            data_presence: null
          }
      }
    },
    props: ['activity_id', 'group_id'],
    methods: {
      addGroup() {
        this.errors = [];
        this.student.activity_id = this.activity_id;
        let dateTime = new Date();
        console.log(this.student.student_id);
        dateTime = (dateTime.getFullYear() + "-" + (dateTime.getMonth()+1) + "-" + dateTime.getDate() + " " + dateTime.getHours() + ":" + dateTime.getMinutes() + ":" + dateTime.getSeconds());
        this.student.data_presence = dateTime;
        axios.post(this.$store.getters.getUrl + 'api/attandancelist', this.student).then((response) =>{
            this.success = response.data['created'];
          })
          .catch(function(error){
            this.errors.push("Something went wrong");
          }.bind(this));
      },
      getStudents(){
         console.log(this.group_id);
        axios.get(this.$store.getters.getUrl + 'api/groups/'+ this.group_id)
         .then(function (response) {
            this.students = response.data.data[0].students;
            this.student.student_id = this.students[0].student_id;
            console.log(this.students);
        }.bind(this))
        .catch((error)=>{
         // this.$router.push('/login'); 
        });
      },
      
    },
    created: function () {
      if(!localStorage.getItem('token') || localStorage.getItem('isAdmin') == 1){
        this.$router.push('/login'); 
      }
      this.getStudents();
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
