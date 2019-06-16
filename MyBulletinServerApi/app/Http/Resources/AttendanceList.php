<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\JsonResource;
use App\AttendanceList as AttandanceListApp;
use App\Activity;
use App\Course;
use App\StudentsGroup;
use App\Http\Resources\StudentsGroups as StudentsGroupsResource;

class AttendanceList extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return array
     */
    public function toArray($request)
    {
        $course_id = Activity::all()->where('activity_id', $this->activity_id)->pluck('course_id');
        $activitiesStudent = AttandanceListApp::all()->where('student_id', $this->student_id)->pluck('activity_id');

        $activities = Activity::all()->where('course_id', $course_id[0])->pluck('activity_id')->toArray();
        $activitiesWithStudent = 0;
        for($i=0; $i<count($activitiesStudent); $i++){
            if(in_array($activitiesStudent[$i], $activities)){
                $activitiesWithStudent++;
            }
        }
        $activities = count($activities);
        $attandanceStudent = $activitiesWithStudent/$activities;
        return [
            'attendance_lists_id ' => $this->attendance_lists_id,
            'data_presence ' => $this->data_presence,
            'student_id ' => $this->student_id,
            'activity_id ' =>  $this->activity_id,
            '$attandanceStudent' =>  $attandanceStudent
        ];
    }
}
