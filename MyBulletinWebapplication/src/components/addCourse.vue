<template>
   <div class="container">
      <div class="row">
        <div class="col-12">
          <h1>Add new course: </h1>
          <div class="form-login align-center">      
              <span class="success">{{success}}</span>             
              <input v-model="course.name" type="text" placeholder="Course name" id="name"/>
              <select v-model="course.group_id" id="groupId">    
                <option value="0" disabled selected>Chose Group</option>   
                <option v-for="group in groupsId" :value="group.id">
                  {{group.name}}
                </option>                                                   
              </select>
              <span class="error" v-for="error in errors">{{error}}</span>
              <button class="u-full-width button-primary" type="submit" v-on:click="addCourse" >Add course</button>
          </div>
       </div>
    </div>
  </div>

</template>

<script>
  import axios from 'axios';
  export default {
    name: 'addCourse',
    data() {
      return {
          errors: [],
          success: '',
          groupsId: [],
          subjects: [],
          subject_name: '',
          course: {
            name: '',
            group_id: 0,
            professor_id: 0,
            subject_id: this.$route.params.id
          }
      }
    },
    watch: {
      'course.group_id': function() {
        var groupName;
        for(var i = 0; i < this.groupsId.length; i++){
          if(this.groupsId[i].id == this.course.group_id){
            groupName = this.groupsId[i].name;
          }
        }
        this.course.name = this.subject_name + "/" + groupName;
      }
    },
    methods: {
      addCourse() {
        this.errors = [];
        if(!this.course.name){
          this.errors.push("Course name is required");
          return;
        }
        if(this.course.group_id===0){
          this.errors.push("Group id is required");
          return;
        }
        this.course.professor_id = localStorage.getItem('professor_id');
        axios.post(this.$store.getters.getUrl + 'api/courses', this.course).then((response) =>{
          this.success = response.data['created'];
        })
        .catch(function(error){
          this.errors.push("Something went wrong");
        }.bind(this));
      },
      getGroupsId() {
        axios.get(this.$store.getters.getUrl + 'api/groups')
         .then(function (response) {
            this.groupsId = response.data.data;
        }.bind(this))
        .catch((error)=>{
          this.$router.push('/login'); 
        });
      },
      getSubjectName() {
        axios.get(this.$store.getters.getUrl + 'api/subjects')
         .then(function (response) {
            this.subjects = response.data.data;
            for(var i = 0; i < this.subjects.length; i++){
              if(this.subjects[i].subject_id == this.course.subject_id){
                this.subject_name = this.subjects[i].name;
              }
            }
            console.log(this.subject_name);
        }.bind(this))
        .catch((error)=>{
          this.$router.push('/login'); 
        });
      }
    },
    created: function () {
      if(!localStorage.getItem('token') || localStorage.getItem('isAdmin') == 1){
        this.$router.push('/login'); 
      }
      this.getGroupsId();
      this.getSubjectName();
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
