package main

import (
	"errors"
	"strings"
)

type BeanType int

const (
	VOIn BeanType = iota
	VOOut
	RPC
	Service
	ServiceImpl
	RMT
	RmtImpl
	Hystrix
	Skeleton
	VO
	Area
	Out
)

var beanTypeArr = [...]string{"VOIn", "VOOut", "RPC",
	"Service", "ServiceImpl", "RMT", "RmtImpl", "Hystrix",
	"Skeleton", "VO", "Area", "Out", "In"}

func String(b BeanType) string {
	return beanTypeArr[b]
}

func GetBeanType(fileName string) (BeanType, error) {
	for index, suffixFileName := range beanTypeArr {
		if strings.HasSuffix(fileName, suffixFileName) {
			return BeanType(index), nil
		}
	}
	return VOIn, errors.New("未定义相应的类型")
}
