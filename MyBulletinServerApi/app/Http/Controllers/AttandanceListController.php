<?php

namespace App\Http\Controllers;

use App\AttendanceList;
use App\Http\Resources\AttendanceList as AttandanceListResource;
use App\Http\Resources\StudentsGroups as StudentsGroupsResource;
use Illuminate\Http\Request;
use App\Activity;
use App\Course;
use App\StudentsGroup;
use Tymon\JWTAuth\Facades\JWTAuth;
use Illuminate\Foundation\Auth\User;

class AttandanceListController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $professor = JWTAuth::parseToken()->authenticate();
        $isAdmin = $professor->isAdmin;
        if(!$isAdmin) {
            $attandanceList = AttendanceList::all();
            return AttandanceListResource::collection($attandanceList);
        }
        return "unauthorized";
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        $professor = JWTAuth::parseToken()->authenticate();
        $isAdmin = $professor->isAdmin;
        if(!$isAdmin) {
            $activity = Activity::all()->where('activity_id', $request->activity_id)->first();
            $activity->checked = true;
            $activity->save();

            AttendanceList::create($request->all());

            return response()->json([
                'created' => 'Student was added to attandance list'
            ], 201);
        }else{
            return "unauthorized";
        }
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        $professor = JWTAuth::parseToken()->authenticate();
        $isAdmin = $professor->isAdmin;
        if(!$isAdmin) {
            $course = Course::all()->where('course_id', $id)->first();
            $studentsGroup = StudentsGroup::all()->where('group_id', $course->group_id);
            $students = StudentsGroupsResource::collection($studentsGroup);


            $ids= array();
            $i = 0;
            foreach($students as $student){
                $activitiesStudent = AttendanceList::all()->where('student_id', $student->student_id)->pluck('activity_id');
                $activities = Activity::all()->where('course_id', $id)->pluck('activity_id')->toArray();
                $activitiesWithStudent = 0;
                for($i=0; $i<count($activitiesStudent); $i++){
                    if(in_array($activitiesStudent[$i], $activities)){
                        $activitiesWithStudent++;
                    }
                }
                $activities = count($activities);

                $attandanceStudent = $activitiesWithStudent/$activities;
                $ids[ $student->student_id]['student'] = $student;
                $ids[ $student->student_id]['total'] = $attandanceStudent;
                $i++;
            }
            return $ids;
        }
        return "unauthorized";
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        $professor = JWTAuth::parseToken()->authenticate();
        $isAdmin = $professor->isAdmin;
        if(!$isAdmin) {
            $attandance = AttendanceList::where('student_id', $id);
            $attandance->delete();

            return response()->json([
                'deleted' => 'Student was deleted'
            ], 200);
        }
    }
}
