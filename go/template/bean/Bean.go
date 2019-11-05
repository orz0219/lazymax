package bean

type Bean struct {
	Name       string
	Fields     []Field
	OtherBeans []Bean
	Model      string
}

type Field struct {
	//名称
	Name string
	// 类型
	Type string
	// 是否为空
	AllowNull string
	// 注释
	Comment string
	// 默认值
	DefaultValue string
}
