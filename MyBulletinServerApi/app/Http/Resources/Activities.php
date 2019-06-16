<?php

namespace App\Http\Resources;

use App\AttendanceList;
use App\Course;
use App\Activity;
use App\Professor;
use App\Student;
use Illuminate\Http\Resources\Json\JsonResource;
use function MongoDB\BSON\toJSON;
use phpDocumentor\Reflection\Types\Object_;
use PhpParser\Node\Expr\Array_;
use function Sodium\add;

class Activities extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return array
     */
    public function toArray($request)
    {
        $students_id = AttendanceList::all()->where('activity_id', $this->activity_id)->pluck('student_id');
        $students = [];
        foreach($students_id as $id) {
            $student = Student::all()->where('student_id', $id);
            $students[$id]['id'] = $student->pluck('student_id')->first();
            $students[$id]['name'] = $student->pluck('name')->first();
            $students[$id]['tagId'] = $student->pluck('tagId')->first();
            $dataPresence = AttendanceList::all()->where('student_id', $id)->pluck('data_presence')->first();
            $students[$id]['datePresence'] = $dataPresence;
            $students[$id]['groupId'] = $student->pluck('group_id')->first();
        }

        /*$groupId = Course::where('course_id', $this->course_id)->pluck('group_id');
        $students_id_All = Student::where('group_id', $groupId)->pluck('student_id');
        $students_All = [];
        foreach($students_id_All as $id) {
            $student = Student::all()->where('student_id', $id);
            $students_All[$id]['id'] = $student->pluck('student_id');
            $students_All[$id]['name'] = $student->pluck('name');
            $students_All[$id]['tagId'] = $student->pluck('tagId');
            $students_All[$id]['groupId'] = $student->pluck('group_id');
        }*/

        return [
            'id' => $this->activity_id,
            'activityDate' => $this->activityDate,
            'hour' => $this->hour,
            'title' => $this->title,
            'duration' => $this->duration,
            'recurrence_id' => $this->recurrence_id,
            'students' => $students,
            'course' => Course::all()->where('course_id', $this->course_id)->first(),
            'checked' => $this->checked
        ];
    }
}
