var myApp = angular.module("myApp", []);
myApp.controller("mayTinh", function($scope)
{
	$scope.ketQua = "Test";
	$scope.tinh = function()
	{
		var so1 = parseInt($scope.no1);
		var so2 = parseInt($scope.no2);
		if ($scope.phepTinh === "+")
		{
			$scope.ketQua = so1 + so2;
		}
		else if ($scope.phepTinh === "-")
		{
			$scope.ketQua = so1 - so2;
		}
		else if ($scope.phepTinh === "*")
		{
			$scope.ketQua = so1 * so2;
		}
		else
		{
			$scope.ketQua = so1 / so2;
		}

	}
});