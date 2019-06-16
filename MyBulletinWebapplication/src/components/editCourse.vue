<template>
   <div class="container">
      <div class="row">
        <div class="col-12">
          <h1>Edit course: </h1>
          <div class="form-login align-center">      
              <span class="success">{{success}}</span>             
              <input v-model="course.name" type="text" placeholder="Course name" id="name"/>
              <select v-model="course.group_id" id="groupId">    
                <option value="0" disabled selected>Chose Group</option>   
                <option v-for="group in groups" :value="group.id">
                  {{group.name}}
                </option>                                                   
              </select>
              <select v-model="course.subject_id" id="groupId">    
                <option value="0" disabled selected>Chose Subject</option>   
                <option v-for="subject in subjects" :value="subject.subject_id">
                  {{subject.name}}
                </option>                                                   
              </select>
              <span class="error" v-for="error in errors">{{error}}</span>
              <button class="u-full-width button-primary" type="submit" v-on:click="saveCourse()" >Edit course</button>
          </div>
       </div>
    </div>
  </div>
</template>

<script>

import axios from 'axios';

export default {
  name: 'editCourse',
  data () {
    return {
      course_id: this.$route.params.id,
      errors: [],
      success: '',
      groups: [],
      subjects: [],
      course: {
        name: '',
        group_id: 0,
        professor_id: 0,
        subject_id: parseInt(this.$route.params.id),
        _method: "PUT"
      }
    }
  },
  methods: {
    saveCourse() {
        console.log(this.course);
        axios.post(this.$store.getters.getUrl + 'api/courses/' + this.course_id, this.course)
        .then((response) =>{
          alert(response.data['updated']);
          getCourse(this.course_id);
        })
        .catch((error)=>{
          this.$router.push('login'); 
        });

    },
    getCourse(id) {
      axios.get(this.$store.getters.getUrl + 'api/courses/' + id + '/edit')
       .then(function (response) {
          this.course.name = response.data.name;
          this.course.group_id = response.data.group_id;
          this.course.professor_id = response.data.professor_id;
        }.bind(this))
        .catch((error)=>{
          //this.$router.push('/login'); 
        });
    },
    getGroups(){
      axios.get(this.$store.getters.getUrl + 'api/groups')
        .then(function (response) {
          this.groups = response.data.data;
          console.log(this.groups);
      }.bind(this))
      .catch((error)=>{
        //this.$router.push('/login'); 
      });
    },
    getSubjects(){
          axios.get(this.$store.getters.getUrl + 'api/subjects')
        .then(function (response) {
            this.subjects = response.data.data;
            console.log(this.subjects)
        }.bind(this))
        .catch((error)=>{
          //this.$router.push('/login'); 
        });
      },
  },
  created: function () {
    if(!localStorage.getItem('token') || localStorage.getItem('isAdmin') == 1){
      this.$router.push('/login'); 
    }
    this.getCourse(18);
    this.getGroups();
    this.getSubjects();
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3,h4{
  text-align: left;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
input, select{
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
a {
  color: #42b983;
}
</style>
