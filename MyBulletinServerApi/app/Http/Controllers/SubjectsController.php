<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Subject;
use App\Course;
use App\Http\Resources\Subject as SubjectResource;
use App\Http\Resources\Courses as CoursesResource;
use Illuminate\Foundation\Auth\User;
use Tymon\JWTAuth\Facades\JWTAuth;

class SubjectsController extends Controller
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
            $subjects = Subject::all()->where('professor_id', $professorId);
            return SubjectResource::collection($subjects);
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
            Subject::create($request->all());

            return response()->json([
                'created' => 'Subject was added'
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
            $course = Course::all()->where('subject_id', $id);
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
            $subject = Subject::all()->where('subject_id', $id);
            return $subject;
        }else{
            return "unauthorized";
        }
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
        $professor = JWTAuth::parseToken()->authenticate();
        $isAdmin = $professor->isAdmin;
        if(!$isAdmin) {
            $subject = Subject::where('subject_id', $id)->first();
            $subject->name = $request->name;
            $subject->save();
            return response()->json([
                'updated' => 'Subject was updated'
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
            $subject = Subject::where('subject_id', $id);
            $subject->delete();

            return response()->json([
                'deleted' => 'Subject was deleted'
            ], 200);
        }else{
            return "unauthorized";
        }
    }
}
