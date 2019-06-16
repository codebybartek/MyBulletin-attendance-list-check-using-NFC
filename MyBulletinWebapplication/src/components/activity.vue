<template>
   <div class="container">
        <div class="row">
          <section class="col-12 header">
            <h1>Activity: {{activity.title}}</h1>
            <h3>{{activity.activityDate}}</h3>
            <div class="data">
              <span class="hours">Hour: {{activity.hour}}</span>
              <span class="startHour">Duration: {{activity.duration}}</span>
            </div>
          </section>
        </div>
        <div class="row top-50">
          <h3>Presence Students: </h3>
            <table class="table table-hover">
                <tr class="header_table">
                    <th>Id</th>
                    <th>Name</th>
                    <th>Tag id</th>
                    <th>Group id</th>
                    <th>Delete</th>
                </tr>
                <tr v-for="student in students">
                    <td>{{student.id}}</td>
                    <td>{{student.name}}</td>
                    <td>{{student.tagId}}</td>
                    <td>{{student.groupId}}</td>
                    <td><button class="delete" v-on:click=deleteStudent(student.id)>&#x274C;</button></td>
                </tr>
            </table>
            <div class="col-12 navigation_buttons">
              <ul>
                <li><router-link :to="{ name: 'addStudentAttandance', params: { activity_id: id, group_id: this.activity.course.group_id  } }">Add student</router-link></li>
              </ul>
            </div>
        </div>
        <section v-if="isChecked() && isToday()" class="row top-50">
          <h3>Check attandance list: </h3>
            <table class="table table-hover">
                <tr class="header_table">
                    <th>Id</th>
                    <th>Name</th>
                    <th>Tag id</th>
                    <th>Presence</th>
                </tr>
                <tr v-for="student in studentsGroup">
                    <td>{{student.student_id}}</td>
                    <td>{{student.name}}</td>
                    <td>{{student.tagId}}</td>
                    <td><button class="presence" v-on:click=addToList(student.student_id)><span v-show="isOnList(student.student_id)">&#x274C;</span></button></td>
                </tr>
            </table>
            <div class="addList col-12">
              <button class="u-full-width button-primary" type="submit" v-on:click="addList()">Add attandance list</button>
            </div>
        </section>
        <section v-if="!isToday()" class="row top-50">
          <h4>You clould only checke attandance list in the same day</h4>
        </section>
    </div>
</template>

<script>

import axios from 'axios';

export default {
  name: 'activity',
  data () {
    return {
      id: this.$route.params.id,
      activity: [],
      students: null,
      attandanceList: [],
      isCheckedBool: 0,
      group: null,
      studentsGroup: null
    }
  },
  methods: {
      getActivities(){
        axios.get('http://vps669485.ovh.net/api/activities/'+ this.id)
         .then(function (response) {
            this.activity = response.data.data[0];
            this.students = response.data.data[0].students;
            console.log(this.students);
            this.getStudentsAll();
        }.bind(this))
        .catch((error)=>{
         // this.$router.push('login'); 
        });
      },
      isToday(){
        let dateActivity = new Date(this.activity.activityDate);
        let currentDate = new Date();

        if(dateActivity.getFullYear() === currentDate.getFullYear() && dateActivity.getMonth() === currentDate.getMonth() && dateActivity.getDate() === currentDate.getDate()){
          return true;
        }else{
            return false;
        }
      },
      getStudentsAll(){
        console.log("Asdasdasdsaasdsad");
        axios.get(this.$store.getters.getUrl + 'api/groups/' + this.activity.course.group_id)
        .then(function (response) {
            this.group = response.data.data[0];
            this.studentsGroup = response.data.data[0].students;
            console.log(this.studentsGroup);
        }.bind(this))
        .catch((error)=>{
          this.$router.push('login'); 
        });
      },
      addToList(id){
        let dateTime = new Date();
        dateTime = (dateTime.getFullYear() + "-" + (dateTime.getMonth()+1) + "-" + dateTime.getDate() + " " + dateTime.getHours() + ":" + dateTime.getMinutes() + ":" + dateTime.getSeconds());
        this.attandanceList.push({student_id: id, activity_id: this.id, data_presence: dateTime});
      },
      isOnList(id){
        for(var i = 0; i < this.attandanceList.length; i++){
          if(this.attandanceList[i].student_id === id){
            return true;
          }
        }
        return false;
      },
      addList(){
        this.activity.checked = true;
        axios.put(this.$store.getters.getUrl + 'api/activities/' + this.activity.id, this.activity).then((response) =>{
            this.success = response.data['created'];
          })
          .catch(function(error){
            this.errors.push("Something went wrong");
          }.bind(this));

        for(var i = 0; i < this.attandanceList.length; i++){
          axios.post(this.$store.getters.getUrl + 'api/attandancelist', this.attandanceList[i]).then((response) =>{
            this.success = response.data['created'];
          })
          .catch(function(error){
            this.errors.push("Something went wrong");
          }.bind(this));
        }
        alert("Attandance list was added");
        
      },
      isChecked(){
        axios.get(this.$store.getters.getUrl + 'api/activities/'+ this.activity.id)
        .then(function (response) {
            this.isCheckedBool = response.data.data[0].checked;
            console.log(this.isCheckedBool);
        }.bind(this))
        .catch((error)=>{
          //this.$router.push('/login'); 
        });
        if(this.isCheckedBool === 1){
          return false;
        }else{
          return true;
        }
      },
      deleteStudent(id){
         axios.delete(this.$store.getters.getUrl + 'api/attandancelist/'+ id)
         .then(function (response) {
            alert(response.data['deleted']);
            this.getGroups();
        }.bind(this))
        .catch((error)=>{
          //this.$router.push('/login'); 
        });
      }
  },
  created: function () {
     if(!localStorage.getItem('token') || localStorage.getItem('isAdmin') == 1){
      this.$router.push('/login'); 
    }
    this.getActivities();
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h4{
  color: #ff1a1a;
  width: 100%;
  text-align: center;
  margin-bottom: 30px;
}
h3{
  text-align: center;
}
.top-50{
  margin-top: 50px;
}
.header{
  text-align: center;
}
.data span {
  display: inline-block;
  padding: 10px 20px;
  position: relative;
  border: 1px solid #ff1a1a;
  border-radius: 30px;
}
.presence{
  background: transparent;
  border: 1px solid #000;
  width: 35px;
  height: 35px;
  font-size: 16px;
  text-align: center;
  vertical-align: middle;
}
.button-primary{
  border: 1px solid #c4c4ef;
  border-radius: 30px;
  background-color: transparent;
  padding: 10px 50px;
  transition: 0.4s;
  outline: none;
  margin: 30px auto;
  cursor: pointer;
}
.button-primary:focus, .button-primary:active, .button-primary:hover, .nav h3:hover{
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
