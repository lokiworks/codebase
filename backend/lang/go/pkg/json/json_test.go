package json

import (
	"encoding/json"
	"testing"

	assert "github.com/stretchr/testify/assert"
)

type Student struct {
	Name string `json:"name"`
	Age  int    `json:"age"`
}

func NewStudent(name string, age int) *Student {
	return &Student{Name: name, Age: age}
}

func TestJson(t *testing.T) {
	jsonStr := `{
   "name":"loki",
   "age":20
}`
	s := Student{}
	err := json.Unmarshal([]byte(jsonStr), &s)
	if err != nil {
		t.Fatal(err)
	}
	assert.Equal(t, "loki", s.Name)
	assert.Equal(t, 20, s.Age)

}
