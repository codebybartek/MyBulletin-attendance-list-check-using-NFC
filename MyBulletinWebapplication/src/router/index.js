import Vue from 'vue'
import Router from 'vue-router'
import login from '@/components/login'
import courses from '@/components/courses'
import course from '@/components/course'
import addStudentAttandance from '@/components/addStudentAttandance'
import editCourse from '@/components/editCourse'
import subjects from '@/components/subjects'
import groups from '@/components/groups'
import group from '@/components/group'
import activity from '@/components/activity'
import addGroup from '@/components/addGroup'
import editGroup from '@/components/editGroup'
import addCourse from '@/components/addCourse'
import addSubject from '@/components/addSubject'
import editSubject from '@/components/editSubject'
import addActivity from '@/components/addActivity'
import CalendarShow from '@/components/CalendarShow'
import Attandancelist from '@/components/attandanceList'
import totalAttandance from '@/components/totalAttandance'
import adminMenu from '@/components/admin/adminMenu'
import adminStudents from '@/components/admin/adminStudents'
import adminProfessors from '@/components/admin/adminProfessors'
import addProfessor from '@/components/admin/addProfessor'
import addAdmin from '@/components/admin/addAdmin'
import addStudent from '@/components/admin/addStudent'
import addStudentGroup from '@/components/addStudentGroup'
import editProfessor from '@/components/admin/editProfessor'
import editStudent from '@/components/admin/editStudent'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'home',
      component: courses
    },
    {
      path: '/students/add',
      name: 'addStudentGroup',
      component: addStudentGroup
    },
    {
      path: '/courses/add/:id',
      name: 'addCourse',
      component: addCourse
    },
    {
      path: '/groups/add',
      name: 'addGroup',
      component: addGroup
    },
    {
      path: '/courses/:id',
      name: 'courses',
      component: courses
    },
    {
      path: '/course/',
      name: 'course',
      props: true,
      component: course
    },
    {
      path: '/addStudentAttandance/',
      name: 'addStudentAttandance',
      props: true,
      component: addStudentAttandance
    },
    {
      path: '/course/edit/:id',
      name: 'editCourse',
      props: true,
      component: editCourse
    },
    {
      path: '/subjects',
      name: 'subjects',
      component: subjects
    },
    {
      path: '/subject/edit/:id',
      name: 'editSubject',
      props: true,
      component: editSubject
    },
    {
      path: '/subjects/add',
      name: 'addSubject',
      component: addSubject
    },
    {
      path: '/groups',
      name: 'groups',
      component: groups
    },
    {
      path: '/group/:id',
      name: 'group',
      component: group
    },
    {
      path: '/group/edit/:id',
      name: 'editGroup',
      props: true,
      component: editGroup
    },
    {
      path: '/login',
      name: 'login',
      component: login
    },
    {
      path: '/activities/:id',
      name: 'activity',
      component: activity
    },
    {
      path: '/activity/add',
      name: 'addActivity',
      component: addActivity
    },
    {
      path: '/attandancelist/add',
      name: 'Attandancelist',
      component: Attandancelist
    },
    {
      path: '/activity/show',
      name: 'CalendarShow',
      component: CalendarShow
    },
    {
      path: '/admin',
      name: 'adminMenu',
      component: adminMenu
    },
    {
      path: '/students',
      name: 'adminStudents',
      component: adminStudents
    },
    {
      path: '/professors',
      name: 'adminProfessors',
      component: adminProfessors
    },
    {
      path: '/professor/add',
      name: 'addProfessor',
      component: addProfessor
    },
    {
      path: '/admin/add',
      name: 'addAdmin',
      component: addAdmin
    },
    {
      path: '/professor/edit/:id',
      name: 'editProfessor',
      component: editProfessor
    },
    {
      path: '/student/add',
      name: 'addStudent',
      component: addStudent
    },
    {
      path: '/student/edit/:id',
      name: 'editStudent',
      component: editStudent
    },
    {
      path: '/attandancelist/:id',
      name: 'totalAttandance',
      component: totalAttandance
    }
  ]
})
