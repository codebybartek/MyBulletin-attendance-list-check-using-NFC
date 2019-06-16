<?php

namespace App\Http\Controllers;

use App\Course;
use App\Http\Resources\Courses;
use App\Http\Resources\Courses as CoursesResource;
use Illuminate\Foundation\Auth\User;
use Illuminate\Http\Request;
use Tymon\JWTAuth\Facades\JWTAuth;

class CoursesController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $professor = JWTAuth::parseToken()->authenticate();
        $professorId = $professor->id;
        $isAdmin = $professor->isAdmin;
        if(!$isAdmin) {
            $courses = Course::all()->where('professor_id', $professorId);
            return CoursesResource::collection($courses);
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
            Course::create($request->all());

            return response()->json([
                'created' => 'Course was added'
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
            $course = Course::all()->where('course_id', $id);
            return CoursesResource::collection($course);
        }else{
            return "unauthorized";
        }
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
        $professor = JWTAuth::parseToken()->authenticate();
        $isAdmin = $professor->isAdmin;
        if(!$isAdmin) {
            $course = Course::all()->where('course_id', $id)->first();
            return $course;
        }else{
            return "unauthorized";
        }
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        $professor = JWTAuth::parseToken()->authenticate();
        $isAdmin = $professor->isAdmin;
        if(!$isAdmin) {
            $course = Course::where('course_id', $id)->first();
            $course->name = $request->name;
            $course->group_id = $request->group_id;
            $course->subject_id = $request->subject_id;
            $course->save();

            return response()->json([
                'updated' => 'Course was updated'
            ], 201);
        }else{
            return "unauthorized";
        }
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
            $course = Course::where('course_id', $id);
            $course->delete();

            return response()->json([
                'deleted' => 'Course was deleted'
            ], 200);
        }else{
            return "unauthorized";
        }
    }
}
